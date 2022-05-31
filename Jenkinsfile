properties([
    buildDiscarder(logRotator(
        artifactDaysToKeepStr: '',
        artifactNumToKeepStr: '',
        daysToKeepStr: '30',
        numToKeepStr: '30')
    )
])


k8s_app({
    git_checkout()

    stage("update") {
        sh """
            rsync -av --delete conf/ \${JOB_DIR}/data/gosquito/conf/conf/
            cp config.toml \${JOB_DIR}/data/gosquito/conf/config.toml
            ls -l \${JOB_DIR}/data/gosquito/conf
        """
    }

    stage("reload") {
        k8s_exec("k8s-1", "dmz", "gosquito-public", "gosquito", "kill -9 1")
    }
})

