# Voice-Recording Application
An android voice recording application developed using Java in Android Studio

# How Does It Work?
### This application features 4 activities namely:
- SplashActivity
-	MainActivity
-	RecordingActivity
-	ListActivity

#	SplashActivity

This activity is the launcher activity that appears for a short time and displays only the logo.
<div align="center">

![image](https://user-images.githubusercontent.com/77288876/184535089-07c21f01-acb6-4487-81dc-8a553dd84b1f.png)

</div>

#	MainActivity

This activity features an ImageView for the logo and two buttons, a record button, and a view list button. The record button starts the Recording Activity when clicked. The view list button starts the List Activity when clicked.

<div align="center">

![image](https://user-images.githubusercontent.com/77288876/184535159-06fab604-f546-4a75-9686-63d8051fbf5a.png)

</div>

#	RecordingActivity

This activity features one record button and a chronometer. When this activity is started, the application requests the user to grant it permission to record audio. The activity is stopped and the MainActivity is launched if the user denies permission. If the user agrees, the recording will begin, and the timer will start.
If the user closes the application whilst recording, the recording will be stopped, timer will stop, and the file will be saved. When it is started after being stopped, the timer will be stopped but upon clicking the record button, the timer will reset and begin counting from zero

![image](https://user-images.githubusercontent.com/77288876/184535190-c7704bef-ecde-4f8e-9fe5-eec686ba8754.png)
![image](https://user-images.githubusercontent.com/77288876/184535226-fc5c5190-b950-4b5c-93d4-191ead614283.png)

# ListActivity
This activity contains a MediaPlayer and a list of all the recordings that have been recorded with this application.
When a recording is clicked, the MediaPlayer is started, and the recording begins to play. The player is at the bottom of the activity and can be swiped up to reveal the seekbar, the pause and repeat buttons.
When the activity is stopped, the MediaPlayer is stopped. The user is able to play and view different recordings from the same activity.

<div align="center">

![image](https://user-images.githubusercontent.com/77288876/184535277-d368f54c-d16e-4f59-a310-553587d1c6e7.png)

</div>










