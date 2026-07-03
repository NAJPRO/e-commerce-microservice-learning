package com.ecommerce.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

}
