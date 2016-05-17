package net.gy.SwiftFrameWork.UI.customwidget.calendar2;

import android.util.Log;

import net.gy.SwiftFrameWork.BuildConfig;

/** Log utility class to handle the log tag and DEBUG-only logging. */
final class Logr {
  public static void d(String message) {
    if (BuildConfig.DEBUG) {
      Log.d("TimesSquare", message);
    }
  }

  public static void d(String message, Object... args) {
    if (BuildConfig.DEBUG) {
      d(String.format(message, args));
    }
  }
}
