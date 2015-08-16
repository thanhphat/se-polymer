package com.chicken.selenium;

import org.openqa.selenium.os.CommandLine;
import org.openqa.selenium.remote.service.DriverService;

import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

public class DriverServiceDestroyer extends Thread  {
    private final DriverService driverService;

    DriverServiceDestroyer(DriverService driverService) {
        this.driverService = driverService;
    }

    public void run() {
        try {
            ReentrantLock e = (ReentrantLock)this.getField("lock");

            try {
                e.lock();
                CommandLine process = (CommandLine)this.getField("process");
                if(process != null) {
                    process.destroy();
                    this.setField("process", null);
                }
            } finally {
                e.unlock();
            }

        } catch (Exception var7) {
            throw new RuntimeException("Unable to shutdown " + this.driverService + ".", var7);
        }
    }

    private Object getField(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        return this.getFieldDefinition(fieldName).get(this.driverService);
    }

    private Field getFieldDefinition(String fieldName) throws NoSuchFieldException {
        Field field = DriverService.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field;
    }

    private void setField(String fieldName, Object value) throws IllegalAccessException, NoSuchFieldException {
        this.getFieldDefinition(fieldName).set(this.driverService, value);
    }
}
