package org.alexandr.kudryavtsev.System.Dates;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter
@Setter
public class Buffer {

    private ArrayList<HomeRequest> buffer;
    final private int sizeBuffer;
    private int oldestRequestIndex;
    private int firstFreeIndex;
    private int lastRequestIndex;

    public Buffer(int size) {
        this.sizeBuffer = size;
        initBuffer(this.sizeBuffer);
        firstFreeIndex = 0;
        oldestRequestIndex = -1;
        lastRequestIndex = -1;
    }

    private void initBuffer(int size) {
        buffer = new ArrayList<>(size);
        for (int i = 0; i < sizeBuffer; i++) {
            buffer.add(null);
        }
    }

    public boolean isEmpty() {
        return lastRequestIndex == -1;
    }

    public void setNewOldestRequestIndex() {
        if (buffer.isEmpty()) {
            this.oldestRequestIndex =  -1;
            return;
        }
        int answerIndex = -1;
        double temp = -1.0;
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) != null && (temp == -1.0 || temp > buffer.get(i).getGeneratedTime())) {
                temp = buffer.get(i).getGeneratedTime();
                answerIndex = i;
            }
        }
        this.oldestRequestIndex = answerIndex;
    }

    public void setNewFirstFreeIndex() {
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) == null) {
                this.firstFreeIndex = i;
                return;
            }
        }
        this.firstFreeIndex = -1;
    }

    public void setNewLastRequestIndex() {
        int answerIndex = -1;
        double temp = -1.0;
        for (int i = 0; i < sizeBuffer; i++) {
            if (buffer.get(i) != null && (temp == -1.0 || temp < buffer.get(i).getGeneratedTime())) {
                temp = buffer.get(i).getGeneratedTime();
                answerIndex = i;
            }
        }
        this.lastRequestIndex = answerIndex;
    }
}
