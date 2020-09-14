package com.salton123.soulove.common.bean;

import com.ximalaya.ting.android.opensdk.model.live.schedule.Schedule;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Author: Thomas.
 * <br/>Date: 2019/8/20 8:30
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:
 */
@Entity
public class PlayHistoryBean {
    public static final String TABLE_NAME = "PlayHistoryBean";

    @PrimaryKey()
    @ColumnInfo(name = "soundId")
    public long soundId;

    @ColumnInfo(name = "groupId")
    public long groupId;

    @ColumnInfo(name = "kind")
    public String kind;

    @ColumnInfo(name = "percent")
    public int percent;

    @ColumnInfo(name = "datatime")
    public long datatime;

    @ColumnInfo(name = "track")
    public Track track;

    @ColumnInfo(name = "schedule")
    public Schedule schedule;

    public long getSoundId() {
        return soundId;
    }

    public void setSoundId(long soundId) {
        this.soundId = soundId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public long getDatatime() {
        return datatime;
    }

    public void setDatatime(long datatime) {
        this.datatime = datatime;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public PlayHistoryBean() {
    }

    @Ignore
    public PlayHistoryBean(long soundId, long groupId, String kind, int percent, long datatime, Track track,
                           Schedule schedule) {
        this.soundId = soundId;
        this.groupId = groupId;
        this.kind = kind;
        this.percent = percent;
        this.datatime = datatime;
        this.track = track;
        this.schedule = schedule;
    }

    @Ignore
    public PlayHistoryBean(long soundId, long groupId, String kind, int percent, long datatime, Track track) {
        this.soundId = soundId;
        this.groupId = groupId;
        this.kind = kind;
        this.percent = percent;
        this.datatime = datatime;
        this.track = track;
    }

    @Ignore
    public PlayHistoryBean(long soundId, long groupId, String kind, long datatime, Schedule schedule) {
        this.soundId = soundId;
        this.kind = kind;
        this.datatime = datatime;
        this.groupId = groupId;
        this.schedule = schedule;
    }

}
