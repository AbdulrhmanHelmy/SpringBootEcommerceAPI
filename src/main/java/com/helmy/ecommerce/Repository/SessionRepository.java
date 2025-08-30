package com.helmy.ecommerce.Repository;

import com.helmy.ecommerce.Model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session,Long> {
}
