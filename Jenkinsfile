def K8S_API = "https://k8s-1-master1.livelace.ru:6443"
def K8S_DEPLOYMENT = "gosquito-public"
def K8S_NS = "data"
def K8S_SA = "k8s-1-jenkins3-sa"
def K8S_TIMEOUT = "60s"
def WEBHOOK_CRED = "github-gosquito-public-webhook"

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
            tokenCredentialId: WEBHOOK_CRED)
    ])
])

k8s_app({
    git_checkout()

    stage("update") {
        sh """
            rsync -av --delete conf/ \${JOB_DIR}/data/gosquito/conf/conf/ && \
            cp config.toml \${JOB_DIR}/data/gosquito/conf/config.toml
        """
    }

    stage("reload") {
        withKubeConfig(
            caCertificate: '', 
            clusterName: '', 
            contextName: '', 
            credentialsId: K8S_SA, 
            namespace: K8S_NS, 
            serverUrl: K8S_API) {

            sh """
                kubectl rollout restart deployment/${K8S_DEPLOYMENT} && \
                kubectl rollout status deployment/${K8S_DEPLOYMENT} --timeout=${K8S_TIMEOUT}
            """
        }
    }
})

