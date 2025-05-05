-- Criação da tb_activity
CREATE TABLE tb_activity (
    id UUID DEFAULT uuid_generate_v4() NOT NULL,
    course_id UUID NOT NULL,
    description VARCHAR(500) NOT NULL,
    point int NOT NULL,
    execute_date DATE NOT NULL,
    availability_date TIMESTAMP NOT NULL,
    activity_type VARCHAR(50) NOT NULL CHECK (activity_type IN ('VIDEO', 'QUIZ', 'TASK')),
    activity_path VARCHAR(500) NOT NULL,
    status varchar(255) NOT NULL,
    create_by varchar(255) NOT NULL DEFAULT 'system_user',
    created_date timestamp DEFAULT CURRENT_DATE,
    last_modified_by varchar(255),
    last_modified_date timestamp DEFAULT CURRENT_DATE,
    FOREIGN KEY (employee_knowledge_advisor_id) REFERENCES tb_employee_knowledge_advisor(id) ON DELETE CASCADE
);
