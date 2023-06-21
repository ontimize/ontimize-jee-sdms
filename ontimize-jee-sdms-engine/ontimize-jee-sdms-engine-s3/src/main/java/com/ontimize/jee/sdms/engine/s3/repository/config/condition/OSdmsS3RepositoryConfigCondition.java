package com.ontimize.jee.sdms.engine.s3.repository.config.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Arrays;
import java.util.List;

public class OSdmsS3RepositoryConfigCondition implements Condition {

    //Properties Key Constants
    public static final String KEY = "ontimize.sdms.s3";

    public OSdmsS3RepositoryConfigCondition() {
    }

    @Override
    public boolean matches( final ConditionContext context, final AnnotatedTypeMetadata metadata ) {
        boolean result = true;
        final Environment environment = context.getEnvironment();
        final List<String> keys = Arrays.asList( "access-key", "secret-key", "region", "bucket" );

        for( int i = 0 ; i < keys.size() && result ; i++ ) {
            final String target = environment.getProperty( KEY.concat( "." ).concat( keys.get( i ) ) );
            result = target != null && ! target.isEmpty();
        }

        return result;
    }
}
