package cn.rabbbbbit.backendstudy;

import lombok.Data;

@Data
public class StudyEntity {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    int age;

}
