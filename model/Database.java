// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package model;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Database
{
    Connection connection;
    Statement statement;
    Map<String, Integer> playerInfo = new HashMap<String, Integer>();
    Set<Map.Entry<String, Integer>> playerInfoSet = playerInfo.entrySet();

    public Database()
    {
        try
        {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tron", "root", "yusuf50");
            statement = connection.createStatement();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public Map<String, Integer> getMap()
    {
        return this.playerInfo;
    }

    public void updateInfo()
    {
        playerInfo.clear();
        playerInfoSet.clear();
        try
        {
            ResultSet res = statement.executeQuery("select * from players");
            while (res.next())
            {
                playerInfo.put(res.getString("playerName"), res.getInt("playerScore"));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void addNewPlayer(String playersName)
    {
        try
        {
            statement.executeUpdate("INSERT INTO tron.players " + "VALUES ('" + playersName + "',0)");
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void increaseScore(String playersName)
    {
        updateInfo();
        int score = 0;
        for (Map.Entry<String, Integer> info : playerInfoSet)
        {
            if (info.getKey().equals(playersName))
            {
                score = info.getValue();
                score++;
                try
                {
                    statement.executeUpdate(( "update`tron`.`players` set `playerScore` = '" + score + "'  where `playerName` = '" + playersName + "'" ));
                    updateInfo();
                } catch (SQLException e)
                {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public boolean isInDatabase(String playersName)
    {
        updateInfo();
        for (Map.Entry<String, Integer> info : playerInfoSet)
        {
            if (info.getKey().equals(playersName))
            {
                return true;
            }
        }
        return false;
    }
}