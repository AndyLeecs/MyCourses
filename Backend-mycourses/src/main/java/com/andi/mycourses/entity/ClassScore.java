package com.andi.mycourses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClassScore extends DBFile{
    public ClassScore(DBFile dbFile)
    {
        this.fileName = dbFile.fileName;
        this.fileType = dbFile.fileType;
        this.data = dbFile.data;
    }
}
