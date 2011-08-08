package com.taobao.tools;

public class PomBean {
	private String groupId = null;
	private String artifactId = null;
	private String version = null;
	
	public PomBean(String groupId, String artifactId, String version){
		this.groupId = groupId;
		this.artifactId = artifactId;
		this.version = version;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getArtifactId() {
		return artifactId;
	}
	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}
