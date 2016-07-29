package com.strictmodenotifier.library;

import android.os.Build;
import com.strictmodenotifier.library.detector.ClassInstanceLimitDetector;
import com.strictmodenotifier.library.detector.CleartextNetworkDetector;
import com.strictmodenotifier.library.detector.CustomSlowCallDetector;
import com.strictmodenotifier.library.detector.Detector;
import com.strictmodenotifier.library.detector.FileUriExposureDetector;
import com.strictmodenotifier.library.detector.LeakedClosableObjectsDetector;
import com.strictmodenotifier.library.detector.NetworkDetector;
import com.strictmodenotifier.library.detector.ResourceMismatchDetector;

public enum ViolationTypeInfo {

  // ThreadPolicy
  CUSTOM_SLOW_CALL(
      "Custom Slow Call",
      ViolationType.CUSTOM_SLOW_CALL,
      Build.VERSION_CODES.HONEYCOMB,
      new CustomSlowCallDetector()),

  NETWORK(
      "Network",
      ViolationType.NETWORK,
      Build.VERSION_CODES.GINGERBREAD,
      new NetworkDetector()),

  RESOURCE_MISMATCHES(
      "Resource Mismatches",
      ViolationType.RESOURCE_MISMATCHES,
      Build.VERSION_CODES.M,
      new ResourceMismatchDetector()),

  // VmPolicy
  CLASS_INSTANCE_LIMIT(
      "Class Instance Limit",
      ViolationType.CLASS_INSTANCE_LIMIT,
      Build.VERSION_CODES.HONEYCOMB,
      new ClassInstanceLimitDetector()),

  CLEARTEXT_NETWORK(
      "Cleartext Network",
      ViolationType.CLEARTEXT_NETWORK,
      Build.VERSION_CODES.M,
      new CleartextNetworkDetector()),

  FILE_URI_EXPOSURE(
      "File Uri Exposure",
      ViolationType.FILE_URI_EXPOSURE,
      Build.VERSION_CODES.JELLY_BEAN_MR2,
      new FileUriExposureDetector()),

  LEAKED_CLOSABLE_OBJECTS(
      "Leaked Closable Objects",
      ViolationType.LEAKED_CLOSABLE_OBJECTS,
      Build.VERSION_CODES.HONEYCOMB,
      new LeakedClosableObjectsDetector()),

  ACTIVITY_LEAKS(
      "Activity Leaks",
      ViolationType.ACTIVITY_LEAKS,
      Build.VERSION_CODES.HONEYCOMB,
      null),

  LEAKED_REGISTRATION_OBJECTS(
      "Leaked Registration_Objects",
      ViolationType.LEAKED_REGISTRATION_OBJECTS,
      Build.VERSION_CODES.JELLY_BEAN,
      null),

  LEAKED_SQL_LITE_OBJECTS(
      "Leaked Sql Lite Objects",
      ViolationType.LEAKED_SQL_LITE_OBJECTS,
      Build.VERSION_CODES.GINGERBREAD,
      null),

  UNKNOWN("UNKNOWN", ViolationType.UNKNOWN, 0, null);

  private String name;
  public final ViolationType violationType;
  public final int minSdkVersion;
  public final Detector detector;

  ViolationTypeInfo(String name, ViolationType violationType, int minSdkVersion,
      Detector detector) {
    this.name = name;
    this.violationType = violationType;
    this.minSdkVersion = minSdkVersion;

    if (detector != null) {
      this.detector = detector;
    } else {
      this.detector = new Detector() {
        @Override public boolean detect(StrictModeLog log) {
          return false;
        }
      };
    }
  }

  public static ViolationTypeInfo convert(ViolationType type) {
    for (ViolationTypeInfo info : values()) {
      if (info.violationType == type) {
        return info;
      }
    }
    return ViolationTypeInfo.UNKNOWN;
  }

  public String violationName() {
    return name + " Violation";
  }
}
