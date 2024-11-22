//By Akansh Grover

package com.Akansh.UniGuide.service;

import java.util.HashMap;

public class FinalizeUserIP {
    static boolean isNumber(String r){
        try{
            Integer.parseInt(r);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }

    static String reverseStr(String r){
        String temp = "";
        for (int i=r.length()-1; i>=0; i--){
            temp = temp + r.charAt(i);
        }
        return temp;
    }

    static String[] breakRoomNo(String r){
        String[] info = new String[3];
        String temp = reverseStr(r);
        info[0] = Integer.toString(Integer.parseInt(reverseStr(temp.substring(3))));//Converted the string to an integer and then back to a string to remove the unnecessary 0
        
        return info;
    }

    public static int[] returnBlock(String rawip){
        HashMap<String, int[]> coord_nodes = NodeInfo.getNodeInfo();
        if (coord_nodes.containsKey(rawip)){
            int[] blocknodes = coord_nodes.get(rawip);
            return blocknodes;
        }
        else{
            if (isNumber(rawip)){
                String b;
                if (rawip.length() >= 4 && rawip.length() <= 5){
                    b = "b" + breakRoomNo(rawip)[0];
                }
                else{
                    b = "-1";
                }
                if (coord_nodes.containsKey(b)){
                    int[] blocknodes = coord_nodes.get(b);
                    return blocknodes;
                }
                else{
                    return new int[] {-1};
                }
            }
            else{
                return new int[] {-1};
            }
        }
    }
}
