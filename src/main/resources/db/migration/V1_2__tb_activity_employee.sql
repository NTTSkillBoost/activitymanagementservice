-- Criação da tb_activity
CREATE TABLE tb_activity_employee (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    activity_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    description VARCHAR(500) NOT NULL,
    percentage_concluded int NOT NULL,
    concluded_date DATE NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date timestamp DEFAULT CURRENT_DATE,
    last_modified_by varchar(255),
    last_modified_date timestamp DEFAULT CURRENT_DATE,
    FOREIGN KEY (activity_id) REFERENCES tb_activity(id) ON DELETE CASCADE
);
