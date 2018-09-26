/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.codename1.uikit.cleanmodern;


import com.twilio.sdk.Twilio;
import com.twilio.sdk.creator.api.v2010.account.MessageCreator;
import com.twilio.sdk.resource.api.v2010.account.Message;
import com.twilio.sdk.type.PhoneNumber;

/**
 *
 * @author user
 */
public class SmSSender {
public static int userID = 1;
    public static String from = "feriel.taboubi";
    public static final String SMTP_HOST_NAME = "smtp.gmail.com";
    public static final String SMTP_AUTH_USER = "feriel.taboubi@esprit.tn";
    public static final String SMTP_AUTH_PWD = "bnnechance";
    public static final String ACCOUNT_SID = "AC786aa8be287364795437b37a9c87f458";
    public static final String AUTH_TOKEN = "f91978171761acc31e86de1f1d5e1560";
    public static final PhoneNumber fromNumber = new PhoneNumber("+12244006210");

    public static void sendSMS(String text, String to) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        MessageCreator creater = Message.create(ACCOUNT_SID,
                new PhoneNumber(to), fromNumber, text);
        Message execute = creater.execute();

    }    
}
