package edu.up.cs371.soccer_application;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import edu.up.cs371.soccer_application.soccerPlayer.SoccerPlayer;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 */
public class SoccerDatabase implements SoccerDB {


    Hashtable<String, SoccerPlayer> playerHashtable = new Hashtable<String, SoccerPlayer>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        if (playerHashtable.containsKey(firstName + lastName)) {
            return false;
        } else {
            SoccerPlayer nextPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            playerHashtable.put(firstName + lastName, nextPlayer);
        }
        return true;
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            playerHashtable.remove(firstName + lastName);
            return true;
        } else
            return false;
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            return playerHashtable.get(firstName + lastName);
        } else
            return null;
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpGoals();
            return true;
        } else
            return false;
    }

    /**
     * increment a player's assists
     *
     * @see SoccerDB#bumpAssists(String, String)
     */
    @Override
    public boolean bumpAssists(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpAssists();
            return true;
        } else {
            return false;
        }

    }

    /**
     * increment a player's shots
     *
     * @see SoccerDB#bumpShots(String, String)
     */
    @Override
    public boolean bumpShots(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpShots();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's saves
     *
     * @see SoccerDB#bumpSaves(String, String)
     */
    @Override
    public boolean bumpSaves(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpSaves();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's fouls
     *
     * @see SoccerDB#bumpFouls(String, String)
     */
    @Override
    public boolean bumpFouls(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpFouls();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpYellowCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        if (playerHashtable.containsKey(firstName + lastName)) {
            getPlayer(firstName, lastName).bumpRedCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {

        int count = 0;
        Enumeration<String> enumKey = playerHashtable.keys();

        while (enumKey.hasMoreElements()) {

            String key = enumKey.nextElement();
            SoccerPlayer player = playerHashtable.get(key);
            if (player.getTeamName().equals(teamName))
                count++;
        }
        return count;
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerNum(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerNum(int idx, String teamName) {
        int count;
        SoccerPlayer player=null;
        Enumeration<String> enumKey = playerHashtable.keys();

        for(count=idx ; count>=0 ; count--){
            if(enumKey.hasMoreElements()) {
                String key = enumKey.nextElement();
                player = playerHashtable.get(key);
            }
            else
                return null;
        }
        return player;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {

        String line;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);



            while((line = bufferedReader.readLine()) != null){
                logString(line);
            }

            bufferedReader.close();
            reader.close();
            return true;

        } catch (IOException e) {
            return file.exists();
        }
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {


        try {

            Enumeration<String> enumKey = playerHashtable.keys();
            SoccerPlayer player;
            PrintWriter writer = new PrintWriter(file);

            while(enumKey.hasMoreElements()){

                player = playerHashtable.get(enumKey.nextElement());

                writer.println("Player: " + player.getFirstName() + " " + player.getLastName() + "\n" +
                        "Uniform Number: " + player.getUniform() + "\n" +
                        "on Team: " + player.getTeamName() + "\n" +
                        "Goals: " + player.getGoals() + "\n" +
                        "Assists: " + player.getAssists() + "'\n" +
                        "Shots: " + player.getShots() + "\n" +
                        "Saves: " + player.getSaves() + "\n" +
                        "Fouls: " + player.getFouls() + "\n" +
                        "Yellow Cards: " + player.getYellowCards() + "\n" +
                        "Red Cards: " + player.getRedCards() + "\n\n");

//                enumKey.nextElement();

            }

            writer.close();

            return true;

        } catch (IOException e) {
            return false;
        }

    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     *
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see edu.up.cs371.soccer_application.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {

        return new HashSet<String>();
    }

}
