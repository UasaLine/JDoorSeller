package com.jds.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "limiting_colors")
@NoArgsConstructor
public class LimitationColors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @OneToOne
    @JoinColumn(name = "master_id")
    private ImageEntity maser;

    @OneToOne
    @JoinColumn(name = "slave_id")
    private ImageEntity slave;

    public LimitationColors(ImageEntity maser, ImageEntity slave) {
        this.maser = maser;
        this.slave = slave;
    }

    public int getSlaveId() {
        if (slave != null) {
            return slave.getId();
        }
        return 0;
    }


}
