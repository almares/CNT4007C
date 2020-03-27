import java.io.*;
import java.net.*;

public class packet
{
    Integer seqNum;
    Integer ID;
    Integer checksum = 0;
    public String word;
    public boolean period = false;

    public packet()
    {
        seqNum = 1;
        ID = 0;
    }

    public void createPacket (String content)
    {
        word = content;
        
        //alternate sequence numbers between sent packets
        if (seqNum == 1)
            seqNum = 0;
        else seqNum = 1;

        //generate checksum of String
        for (int i = 0; i < word.length(); i++)
        {
            checksum += (int)word.charAt(i);

            //determine if last character is a period
            if ((int)word.charAt(i) == 46)
                period = true;
        }
        ID++;
    }

    public String returnMessage()
    {
        //generate packet message format
        return this.seqNum + " " + this.ID + " " + this.checksum + " " + this.word;
    }

    public void splitMessage (String message)
    {
        //split message at whitespaces and assign each packet's variables to the respective message field
        String split [] = message.split("\\s+");
        for (int i = 0; i < split.length; i++)
        {
            seqNum = Integer.parseInt(split[0]);
            ID = Integer.parseInt(split[1]);
            checksum = Integer.parseInt(split[2]);
            word = split[3];
        }
    }

    //account for CORRUPT case
    public String validateMessage()
    {
        //determine current checksum of packet for reference
        Integer refChecksum = 0;
        for (int i = 0; i < word.length(); i++)
        {
            refChecksum += (int)word.charAt(i);
            //determine if last character is a period
            if ((int)word.charAt(i) == 46)
                period = true;
        }

        //if validated checksum
        if (refChecksum.equals(checksum))
            return "ACK" + seqNum.toString();

        //if corrupted checksum, fix corrupted bit
        else 
        {
            if (seqNum == 0)
                seqNum = 1;
            else seqNum = 0;
            return "ACK" + seqNum.toString();
        }
    }
}