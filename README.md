 # chatAPPLocally

This is a chat app that works locally (in local wifi ). 

  **Before using the app :**
  
 - *PS : before run it try to turn off **the mobile data***
 - *PS : use the app in the normal mode **not in the dark mode***
   
## The techniques used in this app are:

  ### Socket connection

  - is used to send messages and recievied between peers.
  
  ### Threads

  - i use thread to let socket connection open parallelly to recieve message and send messeges. 
  - i use graphical thread to update the interface graphic.
     
  ### Recycle View

  - recycle view to add user to list automaticly

  ### ROOM ORM

  - i use simple scheme sender and reciever and messages, to save user and messages .
     
  ### SQLite Database

  - ROOM ORM use SQLite database as base database.
      
  ### Fragment

  - for the fragment first use is in the **AppFeature.class** and use to pass multiple graphic image in the same activity . 
        ***PS** : i could just use normal array and pass the images and then in activity loop the array , but i try to test my use fragemt for my teacher*.
   - for thew second times i used in the **ChattingActivity** and i use it to make easy moving between the chating interface and the user interface .
  ### Android menu
  
  - i use  to update the the ip of the peers in your side **"when you do long press on user it will pop up menu dialog to that have 2 option remove user you pressed on or update "**.

  ### Custom dialog
  
  - for custom dialog i used simple custom diaog that uve 2 button **cancel** or **ok** and input field to entre the info of user.

## The app features are:

  ### and all user are identified by UUID Number that mean it can be blocked
  
  ### Sending messages
  
  ### Receiving messages
  
  ### Adding users
  
  ### Removing users
  
  ### Editing names
  
  ### Saving users
  
  ### Saving Messages
  
  ### Updating IP address automatically


## The Pattern Used are :

  ### Model-View-ViewModel (MVVM)
  
  ### Singleton
  
  ### Builder


## TODO

  - **enhancement UI App , specially Dark Mode**
  - **edit landscape ui option**
  - **update the position in chat fragment to poseitioned in last message**
  - **add the video call using WEBRTC**
  - **block users**
  - **send files**
    
## Requirements
  - Android Studio
  - Java 8
  - Android SDK
  - 
##  Instructions
  1. Clone the repository to your local machine.
  2. Open the project in Android Studio.
  3. Build and run the project.
## License
  **The app is licensed under the MIT License.**
