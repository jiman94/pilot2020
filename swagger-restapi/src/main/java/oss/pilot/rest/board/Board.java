package oss.pilot.rest.board;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oss.pilot.rest.model.CommonDateEntity;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Board extends CommonDateEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;
    @Column(nullable = false, length = 100)
    private String name;
}
