package com.sourtimestudios.www.emotimail.Messaging;

/**
 * Created by user on 16/09/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.sourtimestudios.www.emotimail.Activities.WelcomeActivity;
import com.sourtimestudios.www.emotimail.R;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class GMailer {

    private static final String TAG = "G-Mailer";

        public static void sendMessage(Context context, int fileNumber, String recipient, String sender, String date){

//            String filePath = "/emo" + (fileNumber+1) + ".png";
            File sdCard = Environment.getExternalStorageDirectory();
            String basePath = "file://" + sdCard.getAbsolutePath() + "/aptic/emoticons";

            String fileExtension = "/emo" + (fileNumber+1) + ".png";
            String filePathUri = basePath + fileExtension;
            Uri filePath = Uri.parse(filePathUri);


            Log.i(TAG,"path: " + filePath);
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
//
//            String recipient = prefs.getString(String.valueOf())

            final String username = "apticops@gmail.com";
            final String password = "apticadmin";

            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(recipient));
                message.setSubject("Aptic Message from "+ sender);
//                message.setText("Dear Mail Crawler,"
//                        + "\n\n No spam to my email, please!");

//                message.setFileName(attachmentFilePath);

                // create the message part
                MimeBodyPart messageBodyPart =
                        new MimeBodyPart();

                //fill message
                messageBodyPart.setText("This is an Aptic message from user " + "\""+sender+"\"" +", sent at " + date +
                        ". Please see the attachment.");

                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);

                // Part two is attachment
                messageBodyPart = new MimeBodyPart();
//                DataSource source =
//                        new FileDataSource(context.getFilesDir() + filePath);
                DataSource source =
                        new FileDataSource(filePath.getPath());
                messageBodyPart.setDataHandler(
                        new DataHandler(source));
                messageBodyPart.setFileName(filePath.getPath());
                multipart.addBodyPart(messageBodyPart);

                // Put parts in message
                message.setContent(multipart);

                // Send the message
                Transport.send(message);


//                Transport.send(message);

                Log.i(TAG, "Done");
//                Toast.makeText(context,R.string.message_success,Toast.LENGTH_SHORT).show();

            } catch (MessagingException e) {
                e.printStackTrace();
                Log.e(TAG,"error: " + e.toString());
                throw new RuntimeException(e);
            } catch (Exception e){
                Log.e(TAG,"exception 2: " + e.toString());
                e.printStackTrace();
//                Toast.makeText(,"Failed",Toast.LENGTH_SHORT);
            }

        }

//        public static void main(String[] args) {
//
//            final String username = "your_user_name@gmail.com";
//            final String password = "yourpassword";
//
//            Properties props = new Properties();
//            props.put("mail.smtp.starttls.enable", "true");
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.host", "smtp.gmail.com");
//            props.put("mail.smtp.port", "587");
//
//            Session session = Session.getInstance(props,
//                    new javax.mail.Authenticator() {
//                        protected PasswordAuthentication getPasswordAuthentication() {
//                            return new PasswordAuthentication(username, password);
//                        }
//                    });
//
//            try {
//
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress("your_user_name@gmail.com"));
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse("to_email_address@domain.com"));
//                message.setSubject("Testing Subject");
//                message.setText("Dear Mail Crawler,"
//                        + "\n\n No spam to my email, please!");
//
//
//                Transport.send(message);
//
//                System.out.println("Done");
//
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }
//        }
}

