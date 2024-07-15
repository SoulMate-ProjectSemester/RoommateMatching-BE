import jakarta.persistence.*
import lombok.Getter
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
@Getter
open class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null

//    @CreatedBy
//    @Column(updatable = false)
//    var createdBy: String? = null
//
//    @LastModifiedBy
//    var lastModifiedBy: String? = null
}