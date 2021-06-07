package com.sstec.cdk.rdsvpn.stacks;

import com.sstec.cdk.rdsvpn.MultiStageStackProps;
import com.sstec.cdk.rdsvpn.Tagging;
import software.amazon.awscdk.core.*;
import software.amazon.awscdk.services.route53.*;
import software.amazon.awscdk.services.ssm.StringParameter;
import com.sstec.cdk.rdsvpn.Tagging;


public class ParameterStoreStack extends Stack {

    public ParameterStoreStack(final Construct scope, final String id, final MultiStageStackProps props) {
        super(scope, id, props.props);

        StringParameter writerEndpoint = StringParameter.Builder
                .create(this, "writerEndpoint")
                .parameterName("/config/aws-spring-aurora-cluster/writer.endpoint")
                .stringValue(props.databaseCluster.getClusterEndpoint().getHostname())
                .build();
        Tagging.addEnvironmentTag(writerEndpoint, props);

        StringParameter readerEndpoint = StringParameter.Builder
                .create(this, "readerEndpoint")
                .parameterName("/config/aws-spring-aurora-cluster/reader.endpoint")
                .stringValue(props.databaseCluster.getClusterReadEndpoint().getHostname())
                .build();
        Tagging.addEnvironmentTag(readerEndpoint, props);

    }
}
