package org.sravasti.pubsub.dataflow;

import java.io.Serializable;

public class CitiBike implements Serializable {
	
	@Override
	public String toString() {
		return "CitiBike [tripduration=" + tripduration + ", userType=" + userType + "]";
	}
	private String tripduration;
	private String starttime;
	private String stoptime;
	private String startStationId;
	private String startStationName;
	private String startStationLatitude;
	private String startStationLongitude;
	private String endStationId;
	private String endStationName;
	private String endStationLatitude;
	private String endStationLongitude;
	private String bikeId;
	private String userType;
	private String birthYear;
	private String gender;
	
	public String getTripduration() {
		return tripduration;
	}
	public void setTripduration(String tripduration) {
		this.tripduration = tripduration;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getStoptime() {
		return stoptime;
	}
	public void setStoptime(String stoptime) {
		this.stoptime = stoptime;
	}
	public String getStartStationId() {
		return startStationId;
	}
	public void setStartStationId(String startStationId) {
		this.startStationId = startStationId;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getStartStationLatitude() {
		return startStationLatitude;
	}
	public void setStartStationLatitude(String startStationLatitude) {
		this.startStationLatitude = startStationLatitude;
	}
	public String getStartStationLongitude() {
		return startStationLongitude;
	}
	public void setStartStationLongitude(String startStationLongitude) {
		this.startStationLongitude = startStationLongitude;
	}
	public String getEndStationId() {
		return endStationId;
	}
	public void setEndStationId(String endStationId) {
		this.endStationId = endStationId;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public String getEndStationLatitude() {
		return endStationLatitude;
	}
	public void setEndStationLatitude(String endStationLatitude) {
		this.endStationLatitude = endStationLatitude;
	}
	public String getEndStationLongitude() {
		return endStationLongitude;
	}
	public void setEndStationLongitude(String endStationLongitude) {
		this.endStationLongitude = endStationLongitude;
	}
	public String getBikeId() {
		return bikeId;
	}
	public void setBikeId(String bikeId) {
		this.bikeId = bikeId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

}
