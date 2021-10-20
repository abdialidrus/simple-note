package id.co.abdialidrus.simplenote.business.datasource.cache.category

import id.co.abdialidrus.simplenote.business.domain.model.Category
import id.co.abdialidrus.simplenote.business.domain.util.DomainMapper

class CategoryEntityMapper: DomainMapper<CategoryEntity, Category> {
    override fun mapToDomainModel(model: CategoryEntity): Category {
        return Category(
            id = model.id,
            name = model.name,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt
        )
    }

    override fun mapFromDomainModel(domainModel: Category): CategoryEntity {
        return CategoryEntity(
            id = domainModel.id,
            name = domainModel.name,
            createdAt = domainModel.createdAt,
            updatedAt = domainModel.updatedAt
        )
    }

    fun fromEntityList(initial: List<CategoryEntity>): List<Category>{
        return initial.map { mapToDomainModel(it) }
    }

    fun toEntityList(initial: List<Category>): List<CategoryEntity>{
        return initial.map { mapFromDomainModel(it) }
    }
}