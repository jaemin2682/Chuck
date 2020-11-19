from flask import Flask
from flask import request
from my_clustering import encode, clustering, insert, delete
from mkVideo import makeVideo
# from urllib.parse import quote # url 한글 인코딩 Error 해결

app = Flask(__name__)

@app.route('/cluster')
def cluster():
    groupId = request.args.get('groupId', 'groupId')
    imagePath = request.args.get('imagePath', 'imagePath')
    print(groupId)
    print(imagePath)

    encode(groupId, [imagePath])
    result = clustering(groupId)
    print("결과!!!")
    print(result)

    return result

@app.route('/insert')
def insertPicture():
    groupId = request.args.get('groupId', 'groupId')
    imagePath = request.args.get('imagePath', 'imagePath')
    print(groupId)
    print(imagePath)
    insert(groupId, imagePath)
    return "good"

@app.route('/delete')
def deletePicture():
    groupId = request.args.get('groupId', 'groupId')
    imagePath = request.args.get('imagePath', 'imagePath')
    print(groupId)
    print(imagePath)
    delete(groupId, imagePath)
    return "good"

@app.route('/getInfo')
def getInfo():
    groupId = request.args.get('groupId', 'groupId')
    print(groupId)
    result = clustering(groupId)
    return result

@app.route('/video')
def mkVideo():
    paths = request.args.get('paths', 'paths')
    userid = request.args.get('userid', 'userid')
    music = request.args.get('music', 'music')
    makeVideo(paths, userid, music)
    return "success"

if __name__ == '__main__':
    app.run()
