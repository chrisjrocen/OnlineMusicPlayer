package com.example.musicplayer

class SongInfo{
    //first declare the variables to hold the info of the song
    var songName: String?=null
    var artist: String?=null
    var songUrl: String?=null

    //constructor to initialise the variables above
    constructor(songName: String,artist: String,songUrl: String){//constructor that takes three parameters
        this.songName=songName
        this.artist=artist
        this.songUrl=songUrl
    }
}