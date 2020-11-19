package com.ssafy.chuck.group.service;

import java.util.List;

import com.ssafy.chuck.group.dto.GroupDto;
import com.ssafy.chuck.group.dto.MemberDto;

public interface GroupService {

	// 그룹 생성
	GroupDto create (GroupDto group);

	// 그룹 토큰 업데이트
	void updateToken(int id, String token);

	// 그룹 업데이트
	void update(GroupDto group, long userId);

	// 그룹장 조회
	long readOwner(int id);

	// 그룹 상세 조회
	GroupDto read(long userId, int num, int id);

	// 그룹내 멤버있는지 조회
	boolean isMember(long userId, int id);

	// 그룹 삭제
	void delete(GroupDto group, long userId);

	void deleteMember(int id, long userId, long requestId);

	// 그룹에 멤버 추가
	void createMember(MemberDto member);

	// 그룹내 멤버 리스트 조회
	List<MemberDto> readAllMember(long userId, int num, int id);

	// 유저의 모든 그룹 리스트 조회
	List<GroupDto> readAllGroup(long id);

	// 그룹장 변경
	void change(long ownerId, int num, int id, long userId, GroupDto dto);
}
