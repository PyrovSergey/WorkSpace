/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.soonami;

/**
 * {@Event} представляет собой событие землетрясения. Он содержит детали
   * этого события, такого как название (которое содержит величину и местоположение
   * землетрясения), а также время, и независимо от того, произошло ли цунами
   * предупреждение было выпущено во время землетрясения.
 */
public class Event {

    /** Название события землетрясения*/
    public final String title;

    /** Время, когда произошло землетрясение (в миллисекундах) */
    public final long time;

    /** Было ли выдано предупреждение о цунами (1, если оно было выпущено, 0, если предупреждение не было выпущено) */
    public final int tsunamiAlert;

    /**
     * Constructs a new {@link Event}.
     *
     * @param eventTitle is the title of the earthquake event
     * @param eventTime is the time the earhtquake happened
     * @param eventTsunamiAlert is whether or not a tsunami alert was issued
     */
    public Event(String eventTitle, long eventTime, int eventTsunamiAlert) {
        title = eventTitle;
        time = eventTime;
        tsunamiAlert = eventTsunamiAlert;
    }
}
