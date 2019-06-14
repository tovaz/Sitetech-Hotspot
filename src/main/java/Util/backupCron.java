/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author megan
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
 
/*
 * This class implements a backup scheduler to schedule database backups once a
 * day at a specified time after a day it is started.
 * 
 * Example usage in an application: 
 * BackupScheduler backups = new BackupScheduler("jdbc:derby:salesdb", "D:/backups");
 *
 * backups.start(23) //run database backups at 11PM everyday. 
 *
 * @see java.util.Timer
 * @see java.util.TimerTask
 */
 
public class backupCron extends TimerTask 
{
    private final static long BACKUP_INTERVAL = 1000*60*60*24; //once a day
    private String dbURL;
    private String backupPath;
    private Timer timer;
 
    /* Constructor for Backup Scheduler
     * @parm dbURL  connection URL that should be used to connect to the database.
     * @param backUpPath Location where the backup should be placed.
     */
    public backupCron(String dbURL, String backupPath) {
        this.dbURL = dbURL;
        this.backupPath = backupPath;
    }
 
    /**
     * start the backup scheduler.
     * @param backupTime the time of the date at which to start the backup
     *              in  24-hour clock notation.(eg: 1PM 13))
     */
    public void start(int backupTime)
    {
        timer = new Timer();
        //schedule a backup task everyday at the specified time, starting tomorrow. 
        timer.scheduleAtFixedRate(this,getTomorrowTime(backupTime), BACKUP_INTERVAL);
    }
 
 
    /**
     * Implements TimerTask's run method to perform backups. 
     */
    public void run(){
        try{
            new org.apache.derby.jdbc.EmbeddedDriver();
            Connection conn = DriverManager.getConnection(dbURL);
            backupDatabase(conn);
            conn.close();
        }catch(Exception ex)
        {
            System.out.println("backup failed on:" + dbURL);
            ex.printStackTrace();
        }
    }
 
    /**
         * Performs back up of the database
     * @param conn  Connnection to the database that is to be backed up.
     */
    private void backupDatabase(Connection conn) throws SQLException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
        String backupDirectory = backupPath + "/" + dateFormat.format(new Date());
                String sqlstmt = "CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)";
        CallableStatement cs = conn.prepareCall(sqlstmt); 
        cs.setString(1, backupDirectory);
        cs.execute(); 
        cs.close();
    }
 
        /**
         * Gets the tomorrows Calenders time at the speficied hour.  
         * @param hourOfDay hour the time to be calculated.
         * @return returns tomorrow time.
         */
    private  Date getTomorrowTime(int hourOfDay){
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.roll(Calendar.DATE, true);
        tomorrow.set(Calendar.HOUR_OF_DAY, hourOfDay);
        tomorrow.set(Calendar.MINUTE, 0);
        tomorrow.set(Calendar.SECOND , 0);
        tomorrow.set(Calendar.MILLISECOND, 0);
        return tomorrow.getTime();
    }
}
