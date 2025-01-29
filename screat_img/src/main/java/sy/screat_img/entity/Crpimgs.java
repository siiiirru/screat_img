package sy.screat_img.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="crpimgs")
@Table(
        uniqueConstraints = @UniqueConstraint(name = "uk_user_imgname", columnNames = {"userid", "imgname"})
)
public class Crpimgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false, foreignKey = @ForeignKey(name = "fk_user_crpimgs"))
    private User user;

    @Column(name = "imgname", nullable = false, length = 50)
    private String imgname;

    @Column(name = "img", nullable = false, length = 5000000)
    private String img;
}
