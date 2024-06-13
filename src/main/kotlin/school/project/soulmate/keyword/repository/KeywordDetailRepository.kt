package school.project.soulmate.keyword.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import school.project.soulmate.keyword.entity.Keyword
import school.project.soulmate.keyword.entity.KeywordDetail

@Repository
interface KeywordDetailRepository : JpaRepository<KeywordDetail, Long> {
    fun deleteByKeyword(keyword: Keyword)
}
