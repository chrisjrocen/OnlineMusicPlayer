package com.example.musicplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.song_ticket.view.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    //declare an array of the parameters declared in the songinfo class

    var songList = ArrayList<SongInfo>()

    var adapter:songAdapter?=null

    //to play the song, define the instance of a media player
    var mp: MediaPlayer?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //function to load song is called
        loadUrlOnline()//function declared below

        adapter = songAdapter(songList)
        lvSongs.adapter=adapter

        //tracking
        var tracking =songTrack()
        tracking.start() //to startthe thread


    }

    //function to load the songs online
    fun loadUrlOnline() {

        //load the list of songs in the array declared above
        songList.add(SongInfo("Song 1","Artist 1","http://server6.mp3quran.net/thubti/001.mp3"))//TODO add urls
        songList.add(SongInfo("Song 2","Artist 2","http://server6.mp3quran.net/thubti/002.mp3"))
        songList.add(SongInfo("Song 3","Artist 3","http://server6.mp3quran.net/thubti/003.mp3"))
        songList.add(SongInfo("Song 4","Artist 4","http://server6.mp3quran.net/thubti/004.mp3"))
        //the list is endless
    }

    //define class for the adapter to load ticket into main activity
    inner class songAdapter:BaseAdapter{
        //declare member of the adapter class
        var mySongList=ArrayList<SongInfo>()
        //constructor to initialise members of the adapter class
        constructor(mySongList: ArrayList<SongInfo>):super(){//remeber to implement members of the adapter when error line is displayed
            this.mySongList=mySongList
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            var myView=layoutInflater.inflate(R.layout.song_ticket,null)
            val song = this.mySongList[position] //picks the song from the array

            //the follwing inf will be displayed i
            myView.tvSongName.text = song.songName
            myView.tvSongArtist.text = song.artist

            //no need to display url
            myView.btnPlay.setOnClickListener(View.OnClickListener {
                // PLAY SONG
                if(myView.btnPlay.text.equals("Stop")){
                    mp!!.stop()
                    myView.btnPlay.text ="Start"
                } else {

                    mp= MediaPlayer()//initialise media player
                    try {
                        mp!!.setDataSource(song.songUrl) //set the data source (url) whihc should not be null if the song is to play
                        mp!!.prepare()
                        mp!!.start()
                        //when the song is playing, the play button should change to display stop
                        myView.btnPlay.text = "Stop"

                        //setting the movment of the progress bar
                        pbSong.max=mp!!.duration //setting maximum value to be the duration of the song


                    } catch (ex:Exception){}

                }
            })
            return myView
        }

        override fun getItem(position: Int): Any {
            return this.mySongList[position]  //returns the  item(song)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong() //returns the id of the item(song) if any
        }

        override fun getCount(): Int {
            return this.mySongList.size //returns the number of items(songs)
                                        //used by getview to tell how many uniques tickets to place in the linear layout
        }

    }
    //tracking the progress of the song using a thread
    inner class songTrack(): Thread(){

        //implement the method for run
        override fun run(){
            while (true){
                try {
                    Thread.sleep(1000) //1 millisecond
                }catch (ex:Exception){}

                runOnUiThread{
                    if(mp!=null){ //if mp is not equal to null ->
                    pbSong.progress = mp!!.currentPosition
                }

                }
            }

        }


    }

}
