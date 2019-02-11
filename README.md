# MyWeatherApp
Weather Sample App that uses latest technologies

There are 2 missing features.
1- Background "Service" to update weather.
  For that, I'd use Workmanager to schedule the task every two hours.
  Workmanager has a lot of advantages, one of the best is that it uses a combination of tools to achieve  its purpose
  - Uses JobScheduler for API 23+
  - Uses a custom AlarmManager + BroadcastReceiver implementation for API 14-22
  Therefore it's suitable for most background operations.

2- Notify the user when background update has succeeded

I haven't finished due a lack of time, hope you can check the project to the point I left it.

Thanks for the opportunity and hope to hear from you.