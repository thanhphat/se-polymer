package com.chicken.selenium.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SystemCheck {
    public static boolean isWindows() {
        return osNameMatches("Windows");
    }

    public static boolean isLinux() {
        return osNameMatches("Linux") || osNameMatches("LINUX");
    }

    public static boolean isOSX() {
        return osNameMatches("Mac OS X") || osNameMatches("Darwin");
    }

    private static boolean osNameMatches(String expected) {
        String actual = java.lang.System.getProperty("os.name");
        return actual != null && actual.startsWith(expected);
    }

    public static boolean is32bit() {
        if(isWindows()) {
            return java.lang.System.getenv("PROGRAMFILES(X86)") == null;
        } else {
            assert isLinux() || isOSX();

            String unameM = unameM();
            return "i386".equals(unameM) || "i686".equals(unameM);
        }
    }

    private static String unameM() {
        try {
            Process e = Runtime.getRuntime().exec("uname -m");
            e.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(e.getInputStream()));
            return reader.readLine().trim();
        } catch (IOException var2) {
            return null;
        } catch (InterruptedException var3) {
            return null;
        }
    }

    public static boolean is64bit() {
        if(isWindows()) {
            return java.lang.System.getenv("PROGRAMFILES(X86)") != null;
        } else {
            assert isLinux() || isOSX();

            String unameM = unameM();
            return "x86_64".equals(unameM) || "ia64".equals(unameM) || "amd64".equals(unameM);
        }
    }
}
