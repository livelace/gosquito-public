import ru.livelace.jgl.Vault

def VAULT = new Vault(this)

def K8S_API = "https://k8s-4-master-1.livelace.ru:6443"
def K8S_DEPLOYMENT = "gosquito-public"
def K8S_NS = "gosquito"
def K8S_SA = "ci-k8s-4"
def K8S_TIMEOUT = "60s"
def WEBHOOK_TOKEN = VAULT.getSecret("string, null, self/node/zmd/jenkins/webhook/gosquito-public, token")[0]

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
            token: WEBHOOK_TOKEN,
            tokenCredentialId: '')
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

