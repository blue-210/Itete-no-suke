ALTER TABLE sukeroku.pain_records
ALTER COLUMN pain_record_id TYPE char(36) USING pain_record_id::char;
