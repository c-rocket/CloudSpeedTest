package com.oracle.cloud.speedtest.model;

public class TestObject {

	private long id;
	private String indexedName;
	private String unindexedName;

	public TestObject(long id, String indexedName, String unindexedName) {
		super();
		this.id = id;
		this.indexedName = indexedName;
		this.unindexedName = unindexedName;
	}

	public long getId() {
		return id;
	}

	public String getIndexedName() {
		return indexedName;
	}

	public String getUnindexedName() {
		return unindexedName;
	}

}
