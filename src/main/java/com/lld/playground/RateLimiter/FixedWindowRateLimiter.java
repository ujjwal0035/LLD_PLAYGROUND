package com.lld.playground.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter {
    private final int maxAllowedSize;
    private final long windowSizeInMillis;

    private final ConcurrentHashMap<String, WindowState> userSate;

    private static class WindowState{
        private final long window;
        private final int reqCount;

        WindowState(long windowValue, int count){
            this.window = windowValue;
            this.reqCount = count;
        }
    }

    public FixedWindowRateLimiter(int maxAllowedSize, long windowSizeInMillis){
        this.maxAllowedSize = maxAllowedSize;
        this.windowSizeInMillis = windowSizeInMillis;

        userSate = new ConcurrentHashMap<>();
    }

    public boolean isRequestAllowed(String userId){
        long windowValue = System.currentTimeMillis() / this.windowSizeInMillis;

        // compute the hashmap entry for the userId
        // if userId is not presetnt then return the new WindowState with count 1
        // if userId present then check for the window value, if it is same then increment the count and check for maxAllowedSize
        // if the window value is deferent then reset the count to 1 and update the window value

        WindowState state = userSate.compute(userId, (key, existingState)->{

            // checking the userId is not present or window value is different
            if(existingState == null || existingState.window != windowValue){
                return new WindowState(windowValue, 1);
            }

            return new WindowState(windowValue, existingState.reqCount + 1);
        });

        return state.reqCount < this.maxAllowedSize;
    }
}
