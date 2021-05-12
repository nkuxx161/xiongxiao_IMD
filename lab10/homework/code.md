## 前端

### activty_main.xml

```xml
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/notify"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/notify_me"
        app:layout_constraintBottom_toTopOf="@+id/update"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/update"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/update_me"
        app:layout_constraintBottom_toTopOf="@+id/cancel"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/notify" />

    <Button
        android:id="@+id/cancel"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/cancel_me"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/update" />


</android.support.constraint.ConstraintLayout>
```

## 后端

###  点击notify me发送通知

```java
public void sendNotification() {

    // Sets up the pending intent to update the notification.
    // Corresponds to a press of the Update Me! button.
    Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
    PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,
                                                                   NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT);

    // Build the notification with all of the parameters using helper
    // method.
    NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

    // Add the action button using the pending intent.
    notifyBuilder.addAction(R.drawable.ic_update,
                            getString(R.string.update), updatePendingIntent);

    // Deliver the notification.
    mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    // Enable the update and cancel buttons but disables the "Notify
    // Me!" button.
    setNotificationButtonState(false, true, true);
}

private NotificationCompat.Builder getNotificationBuilder() {

    // Set up the pending intent that is delivered when the notification
    // is clicked.
    Intent notificationIntent = new Intent(this, MainActivity.class);
    PendingIntent notificationPendingIntent = PendingIntent.getActivity
        (this, NOTIFICATION_ID, notificationIntent,
         PendingIntent.FLAG_UPDATE_CURRENT);

    // Build the notification with all of the parameters.
    NotificationCompat.Builder notifyBuilder = new NotificationCompat
        .Builder(this, PRIMARY_CHANNEL_ID)
        .setContentTitle(getString(R.string.notification_title))
        .setContentText(getString(R.string.notification_text))
        .setSmallIcon(R.drawable.ic_android)
        .setAutoCancel(true).setContentIntent(notificationPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL);
    return notifyBuilder;
}

public void createNotificationChannel() {

    // Create a notification manager object.
    mNotifyManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

    // Notification channels are only available in OREO and higher.
    // So, add a check on SDK version.
    if (android.os.Build.VERSION.SDK_INT >=
        android.os.Build.VERSION_CODES.O) {

        // Create the NotificationChannel with all the parameters.
        NotificationChannel notificationChannel = new NotificationChannel
            (PRIMARY_CHANNEL_ID,
             getString(R.string.notification_channel_name),
             NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription
            (getString(R.string.notification_channel_description));

        mNotifyManager.createNotificationChannel(notificationChannel);
    }
}
```

### 点击update更新通知（inboxstyle）

```java
public void updateNotification() {

    // Build the notification with all of the parameters using helper
    // method.
    NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

    // Update the notification style to inboxstyle.
    notifyBuilder.setStyle(new NotificationCompat.InboxStyle()
                           .setBigContentTitle(getString(R.string.notification_updated))
                           .setSummaryText("一共三行通知")
                           .addLine("第一行通知")
                           .addLine("第二行通知")
                           .addLine("第三行通知"));

    // Deliver the notification.
    mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

    // Disable the update button, leaving only the cancel button enabled.
    setNotificationButtonState(false, false, true);
}
```

###  取消通知信息

```java
public void cancelNotification() {
    // Cancel the notification.
    mNotifyManager.cancel(NOTIFICATION_ID);

    // Reset the buttons.
    setNotificationButtonState(true, false, false);
}
```

