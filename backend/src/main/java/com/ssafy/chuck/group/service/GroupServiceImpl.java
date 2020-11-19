package com.ssafy.chuck.group.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import com.ssafy.chuck.common.annotation.ChangeLog;
import com.ssafy.chuck.common.annotation.GroupMemberCheck;
import com.ssafy.chuck.common.annotation.GroupOwnerCheck;
import com.ssafy.chuck.common.annotation.JoinLog;
import com.ssafy.chuck.common.annotation.SignOutLog;
import com.ssafy.chuck.error.exception.AccessDeniedException;
import com.ssafy.chuck.group.dao.GroupDao;
import com.ssafy.chuck.group.dto.GroupDto;
import com.ssafy.chuck.group.dto.MemberDto;

@Service
public class GroupServiceImpl implements GroupService {

	@Autowired
	GroupDao dao;

	@Override
	public GroupDto create(GroupDto group) {
		try {
			dao.create(group);
			String token = JWT.create().withIssuer("Chucks")
				.withSubject("Invite group")
				.withClaim("id", group.getId())
				.sign(Algorithm.HMAC256("chuck_project"));
			this.updateToken(group.getId(), token);
			this.createMember(new MemberDto(group.getId(), group.getUserId(), true));
		} catch (DataAccessException e) {
			throw e;
		}
		return read(group.getUserId(), 0, group.getId());
	}

	@Override
	public void updateToken(int id, String token) {
		try {
			dao.updateToken(id, token);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	// @GroupOwnerCheck
	@Override
	public void update(GroupDto group, long userId) {
		try {
			dao.update(group);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public long readOwner(int id) {
		return dao.readOwner(id);
	}

	@GroupMemberCheck
	@Override
	public GroupDto read(long userId, int num, int id) {
		return dao.read(id);
	}

	@Override
	public boolean isMember(long userId, int id) {
		return dao.isMember(userId, id);
	}

	@GroupOwnerCheck
	@Override
	public void delete(GroupDto group, long userId) {
		try {
			dao.deleteMember(group.getId(), userId);
			dao.delete(group.getId());
		} catch (DataAccessException e) {
			throw e;
		}
	}
	@SignOutLog
	@Override
	public void deleteMember(int id, long userId, long requestId) {
		if(userId == requestId || requestId == readOwner(id)) {
			dao.deleteMember(id, userId);
		} else {
			throw new AccessDeniedException("권한이 없습니다.");
		}
	}

	@JoinLog
	@Override
	public void createMember(MemberDto member) {
		try {
			dao.createMember(member);
		} catch (DataAccessException e) {
			throw e;
		}
	}

	@Override
	public List<MemberDto> readAllMember(long userId, int num, int id) {
		return dao.readAllMember(id);
	}

	@Override
	public List<GroupDto> readAllGroup(long id) {
		return dao.readAllGroup(id);
	}

	@ChangeLog
	@GroupMemberCheck
	@Override
	public void change(long ownerId, int num, int id, long userId, GroupDto dto) {
		if(ownerId != readOwner(dto.getId())) throw new AccessDeniedException("그룹장 체크");
		try {
			dao.changeMemberFalse(ownerId);
			dao.changeMemberTrue(userId);
			dao.change(dto.getId(), userId);
		} catch (DataAccessException e) {
			throw e;
		}
	}
}
