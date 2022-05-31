properties([
    buildDiscarder(logRotator(
        artifactDaysToKeepStr: '',
        artifactNumToKeepStr: '',
        daysToKeepStr: '30',
        numToKeepStr: '30')
    ),
    disableConcurrentBuilds(),
    pipelineTriggers([
        GenericTrigger(
            causeString: 'Generic Cause', 
            printContributedVariables: true, 
            printPostContent: true, 
            regexpFilterExpression: '', 
            regexpFilterText: '', 
            silentResponse: true, 
            token: '', 
            tokenCredentialId: 'github-gosquito-public-webhook')
    ])
])

k8s_app({
    git_checkout()

    stage("update") {
        sh """
            rsync -av --delete conf/ \${JOB_DIR}/data/gosquito/conf/conf/
            cp config.toml \${JOB_DIR}/data/gosquito/conf/config.toml
        """
    }

    //stage("reload") {
    //    k8s_exec("k8s-1", "dmz", "gosquito-public", "gosquito", "kill -HUP 1")
    //}

    withKubeConfig(
        caCertificate: '', 
        clusterName: '', 
        contextName: '', 
        credentialsId: 'k8s-1-jenkins3-sa', 
        namespace: 'dmz', 
        serverUrl: 'https://k8s-1-master1.livelace.ru:6443') {
        
        sh """
            kubectl rollout restart deployment gosquito-public
        """
    }
})

