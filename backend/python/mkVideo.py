import re
import os
import cv2
import numpy as np
from PIL import Image
import matplotlib.pyplot as plt
from moviepy.editor import *
from PIL import ImageDraw

# pip install moviepy


def makeVideo(paths, userid, music):
    paths = paths[:-1]
    pathList = paths.split(":")


    background = Image.open("film_frame.png")



    #1. 이미지 resize
    #2. 이미지 영역 확대(1080, 720) 및 가운데 정렬
    #3. 배경에 이미지 병합
    fps, width, height = 10, 800, 532
    frame_array = []
    # plt.imshow(background)
    # plt.show()
    # 1080 - 800 = 280, 720 - 532 = 188 (140,94)
    background = cv2.imread("film_frame.png")

    # 사진 800/532, 전체 1080*720
    for idx , path in enumerate(pathList) : 
        if(idx == len(pathList) - 1):
            break
        weight, img2, img1 = 0, cv2.resize(cv2.imread(pathList[idx]), (width, height)), cv2.resize(cv2.imread(pathList[idx+1]), (width, height))
        # weight, img2, img1 = 0, 
        row, col = img1.shape[:2]
        translation = np.float32([[1, 0, 280], [0, 1, 200]])
        img_translation = cv2.warpAffine(img1, translation, (col+280, row+188))
        translation = np.float32([[1, 0, -140], [0, 1, -100]])
        img_translation = cv2.warpAffine(img_translation, translation, (col+280, row+188))
        img1 = background + img_translation
        background = cv2.imread("film_frame.png")
        # row, col = img.shape[:2]
        translation = np.float32([[1, 0, 280], [0, 1, 200]])    # 188
        img_translation = cv2.warpAffine(img2, translation, (col+280, row+188))
        translation = np.float32([[1, 0, -140], [0, 1, -100]]) # -94
        img_translation = cv2.warpAffine(img_translation, translation, (col+280, row+188))
        img2 = background + img_translation

        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)
        frame_array.append(img2)

        # fade in / fade out
        while(weight <= 1.0):
            postWeight = 1.0 - weight
            dst = cv2.addWeighted(img1, weight, img2, postWeight, 0)
            frame_array.append(dst)
            weight = weight + 0.1
    out = cv2.VideoWriter("videos/" + userid + "/prepare.mp4",cv2.VideoWriter_fourcc(*'DIVX'), fps, (1080, 720))
    print(len(frame_array))
    for i in range(len(frame_array)):
        out.write(frame_array[i])
    out.release()


    # 오디오 입히기
    videoclip = VideoFileClip("videos/" + userid + "/prepare.mp4")
    videoclip2 = VideoFileClip("videos/" + userid + "/prepare.mp4")
    
    audioclip = AudioFileClip(music)
    audioclip.duration = videoclip.duration
    videoclip.audio = audioclip
    videoclip.write_videofile("videos/" + userid + "/final.mp4")

    audioclip2 = AudioFileClip("silence.mp3")
    audioclip2.duration = videoclip.duration
    videoclip2.audio = audioclip2
    videoclip2.write_videofile("videos/" + userid + "/middle.mp4")

    # videoclip2.write_videofile("videos/" + userid + "/middle.mp4")


def main():
    makeVideo("dataset\\1.jpg:dataset\\102701997.2.jpg:dataset\\1809170726057320_w.jpg:dataset\\2.jpg:dataset\\20191108181733_5dc532adcc402_1.jpg:", "5", "Fingertips.mp3")



    # 동영상 제작
    # paths = [os.path.join('dataset', path) for path in os.listdir('dataset')]
    # print(paths)
    # fps, width, height = 10, 500, 500
    # frame_array = []

    # for idx , path in enumerate(paths) : 
    #     if(idx == len(paths) - 1):
    #         break
    #     weight, img2, img1 = 0, cv2.resize(cv2.imread(paths[idx]), (width, height)), cv2.resize(cv2.imread(paths[idx+1]), (width, height))
    #     frame_array.append(img2)
    #     frame_array.append(img2)
    #     # fade in / fade out
    #     while(weight <= 1.0):
    #         postWeight = 1.0 - weight
    #         dst = cv2.addWeighted(img1, weight, img2, postWeight, 0)
    #         frame_array.append(dst)
    #         weight = weight + 0.2
    # out = cv2.VideoWriter("middle.mp4",cv2.VideoWriter_fourcc(*'DIVX'), fps, (width, height))
    # print(len(frame_array))
    # for i in range(len(frame_array)):
    #     out.write(frame_array[i])
    # out.release()


    # # 오디오 입히기
    # videoclip = VideoFileClip("middle.mp4")
    # audioclip = AudioFileClip("Fingertips.mp3")
    # audioclip.duration = videoclip.duration
    # videoclip.audio = audioclip
    # videoclip.write_videofile("final.mp4")


if __name__ == "__main__":
    main()
