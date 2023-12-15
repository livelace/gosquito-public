libraries {
    git {
        repo_url = "https://github.com/livelace/gosquito-public.git"
    }
    k8s_app {
        cluster = "k8s-4"
        namespace = "gosquito"

        image = "registry.livelace.ru/infra/portage-gosquito-update:latest"

        volume = """
            gosquito-public-conf, data/gosquito/conf, rw
        """
    }
}

