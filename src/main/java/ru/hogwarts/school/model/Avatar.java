package ru.hogwarts.school.model;

import jakarta.persistence.*;

import java.util.Arrays;

@Entity
public class Avatar {
    @Id
    @GeneratedValue
    Long id;
    String filePath;
    long fileSize;
    String mediaType;
    @Lob
    byte[] data;

    @OneToOne
    Student student;

    public Avatar(){

    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public byte[] getData() {
        return data;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public Student getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "id=" + id +
                ", filePath='" + filePath + '\'' +
                ", fileSize=" + fileSize +
                ", mediaType='" + mediaType + '\'' +
                ", data=" + Arrays.toString(data) +
                ", student=" + student +
                '}';
    }
}
