package softuni.exam.models.enums;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum ApartmentType {
    @XmlEnumValue("two_rooms")
    TWO_ROOMS,

    @XmlEnumValue("three_rooms")
    THREE_ROOMS,

    @XmlEnumValue("four_rooms")
    FOUR_ROOMS
}