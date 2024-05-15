package school.project.soulmate.gptApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Assistant(
    @Id
    var id: Int,
    var assistantId: String,
)
