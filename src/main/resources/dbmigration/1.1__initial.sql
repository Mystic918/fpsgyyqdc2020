-- apply changes
alter table survey_enterprise add column deleted_at datetime(6);

alter table survey_person add column deleted_at datetime(6);

