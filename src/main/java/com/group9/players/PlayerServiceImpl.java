package com.group9.players;

import com.group9.dao.PlayerDAO;
import com.group9.teams.Team;
import com.group9.teams.TeamService;
import com.group9.login.User;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 

 
@Service("playerService")
@Transactional
public class PlayerServiceImpl implements PlayerService {
 
    @Autowired
    private PlayerDAO dao;
    
    @Autowired
    TeamService teamService;
    
    
    /**
     * Forward request to DAO and return Player
     * @param id
     * @return Player
     */
    @Override
    public Player findById(int id) {
        return dao.findById(id);
    }
 
    /**
     * Save new player using DAO
     * @param player 
     */
    @Override
    public void savePlayer(Player player) {
        dao.savePlayer(player);
    }
 
    /**
     * Get latest revision of player and return it
     * @param player 
     */
    @Override
    public void getUpdatePlayer(Player player) {
        Player entity = dao.findById(player.getId());
        if(entity!=null){
            entity.setForename(player.getForename());
            entity.setSurname(player.getSurname());
            entity.setSalary(player.getSalary());
            entity.setDefense(player.getDefense());   
        }
    }
 
    @Override
    public void deletePlayerById(int id) {
        dao.deletePlayerById(id);
    }
     
    @Override
    public List<Player> findAllPlayers() {
        return dao.getAllPlayers();
    }
 
 
 
    @Override
    public boolean isPlayerIDUnique(int id) {
        Player player = findById(id);
        return ( player == null || ((player.getId() == id)));
    }
    
    /**
     * Generate X number of new players and store them in database
     * Forename and surname are taken randomly from github CSV file
     * @param numberOfPlayers
     * @return 
     */
    @Override
    public ArrayList<Player> generateRandomPlayers(int numberOfPlayers){
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < numberOfPlayers; i++) {
                try {
                     URL url12 = new URL("https://raw.githubusercontent.com/fivethirtyeight/data/master/most-common-name/adjusted-name-combinations-list.csv");
                     URLConnection urlConn = url12.openConnection();
                     InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
                     BufferedReader buff = new BufferedReader(inStream);
                     buff.readLine();
                     String content2 = buff.readLine();
                     ArrayList<String> forenames = new ArrayList<>();
                     ArrayList<String> surnames = new ArrayList<>();
                     while (content2 != null) {
                         String[] splitted = content2.replaceAll("\"", "").split(",");
                         forenames.add(splitted[1]);
                         surnames.add(splitted[2]);
                         content2 = buff.readLine();
                     }


                     Random r = new Random();
                     Player player = new Player();
                     player.setAge(r.nextInt(60-15) + 15);
                     player.setSalary(r.nextInt(1000000-1) + 1);
                     player.setDefense(r.nextInt(99-1) + 1);
                     player.setOffense(r.nextInt(99-1) + 1);
                     player.setForename(forenames.get((new Random()).nextInt(forenames.size())));
                     player.setSurname(surnames.get((new Random()).nextInt(surnames.size())));
                     player.setInTeam(0);

                     savePlayer(player);
                     players.add(player);
                 } catch (IOException e) {
                     System.out.print("Failed to load names from csv");
                 }
            }
        return players;
    }
    /**
     * Overwrite Player with new player information (update player)
     * @param player 
     */
    @Override
    public void updatePlayer(Player player) {
        dao.updatePlayer(player);
    }
    
    /**
     * Return All player of specific user
     * @param user
     * @return 
     */
    @Override
    public ArrayList<Player> getPlayers(User user){
        ArrayList<Player> players = new ArrayList<>();
        
        Team t = teamService.findByName(user.getTeamName());
        players.add(findById(t.getPlayer1ID()));
        players.add(findById(t.getPlayer2ID()));
        players.add(findById(t.getPlayer3ID()));
        players.add(findById(t.getPlayer4ID()));
        players.add(findById(t.getPlayer5ID()));
        players.add(findById(t.getPlayer6ID()));
                
        return players;
    }
    /**
     * Returns all users that are not in any team
     * @return 
     */
    @Override
    public ArrayList<Player> getFreePlayers(){
        ArrayList<Player> freePlayers = new ArrayList<>();
        
        for(Player player: findAllPlayers()){
            if(player.getInTeam() == 0){
                freePlayers.add(player);
            }
        }
        return freePlayers;
    
    }
    
    
    
}