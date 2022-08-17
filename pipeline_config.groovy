libraries {
    git {
        repo_url = "git@github.com:livelace/gosquito-public.git"
    }
    k8s_app {
        cluster = "k8s-1"
        namespace = "task"

        image = "harbor-core.k8s-2.livelace.ru/infra/tools:22.04"

        volume = """
            task-gosquito-public-conf, data/gosquito/conf, rw
        """
    }
}

