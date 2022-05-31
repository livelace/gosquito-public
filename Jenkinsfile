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
            ls -l \${TEMP_DIR}
            ls -l \${WORK_DIR}
            ls -l data/gosquito/conf
        """
    }

    stage("reload") {
        k8s_exec("k8s-1", "dmz", "gosquito-public", "gosquito", "kill -9 1")
    }
})

