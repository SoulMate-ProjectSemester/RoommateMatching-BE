package school.project.soulmate.common.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MapsId
import jakarta.persistence.OneToOne
import lombok.Setter
import school.project.soulmate.member.entity.Member

@Entity
@Setter
class MemberRefreshToken(
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "member_id")
    val member: Member,
    var refreshToken: String,
) {
    @Id
    val memberId: Long? = null
}
