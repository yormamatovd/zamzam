package client.entity;

import client.entity.abs.AbsMain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client extends AbsMain {

    @Column(nullable = false, updatable = false, unique = true)
    private Long infoId;
}
